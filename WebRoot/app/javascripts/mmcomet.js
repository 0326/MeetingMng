var MMComet = {};

MMComet.socket = null;

MMComet.connect = (function(host) {
    if ('WebSocket' in window) {
        MMComet.socket = new WebSocket(host);
    } else if ('MozWebSocket' in window) {
        MMComet.socket = new MozWebSocket(host);
    } else {
        console.log('Error: WebSocket is not supported by this browser.');
        return;
    }

    MMComet.socket.onopen = function () {
        console.log('Info: WebSocket connection opened.');
        
    };

    MMComet.socket.onclose = function () {
        
        console.log('Info: WebSocket closed.');
    };

    MMComet.socket.onmessage = function (message) {
        // console.log(message.data);
        MMComet.showMsg($.parseJSON(message.data));
    };
});

MMComet.initialize = function(username) {
    if (window.location.protocol == 'http:') {
        MMComet.connect('ws://' + window.location.host + '/MeetingMng/conn?username='+username);
    } else {
        MMComet.connect('wss://' + window.location.host + '/MeetingMng/conn?username='+username);
    }
};

MMComet.sendMessage = (function() {
    var message = document.getElementById('MMComet').value;
    if (message != '') {
        MMComet.socket.send(message);
        document.getElementById('MMComet').value = '';
    }
});

MMComet.showMsg = function(data){

    Messenger().post({
      message: '<a href="/MeetingMng/u#/message">您收到一条'+MMessageParse.getMsgType(data.type)+'  来自'+data.from+'</a>',
      type: 'success',
      showCloseButton: true
    });
}

window.MMComet = MMComet;

/////////////////////////////////消息解析对象
var MMessageParse = {};
//获取会议类型通知
MMessageParse.getMsgType = function(type){
    var res = "通知消息";
    if(type == "pinvi*****"){
        res = "会议邀请通知";
    }
    else if(type == "oinvi*****"){
        res = "办会邀请通知"
    }
    else if(type == "oagre*****"){
        res = "同意邀请通知";
    }
    else{
        //
    }
    return res;
}
//根据会议类型解析body字段，生成消息主题返回给用户
MMessageParse.getMsgBody = function(type, body){
    var res = "这是消息主体";
    if(type == "oinvi*****" || type == "pinvi*****"){
        //{meetingName,meetingStartTime,meetingContent,meetingLocation} 
        var strArr = ['会议名：',body.meetingName,';会议内容：',body.meetingContent,';会议地点：',
            body.meetingLocation,';开会时间：',(new Date(parseInt(body.meetingStartTime))).toLocaleString()];

        res = strArr.join("");
    }
    else if(type == "oagre*****"){
        res = "同意邀请通知";
    }
    else{
        //
    }
    return res;
}
//根据会议类型解析body字段，返回相关id
MMessageParse.getMsgRid = function(type, body){
    var res = null;
    if(type == "oinvi*****" || type == "pinvi*****"){
        res = body.meetingId;
    }
    else if(type == "oagre*****"){
        res = body.meetingId;
    }
    else{
        //
    }
    return res;
}

window.MMessageParse = MMessageParse;

//设置闹钟
var MAlarm = {
    myAudioContext: null,
    mySource: null,
    myBuffer: null,
    play: function(){
        if(navigator.userAgent.indexOf("Safari")>-1 && navigator.userAgent.indexOf("Safari")>-1){
            //safari
            // playBase64();
        }
        else{
            document.getElementById("alarm-audio").play();
        }
    },
    // playBase64(): function(){
    //     var arrayBuff = Base64Binary.decodeArrayBuffer(sound);
    //     MAlarm.myAudioContext.decodeAudioData(arrayBuff, function(audioData) {
    //         MAlarm.myBuffer = audioData;
    //     });
        
    //     if ('AudioContext' in window) {
    //         MAlarm.myAudioContext = new AudioContext();
    //     } 
    //     else if ('webkitAudioContext' in window) {
    //         MAlarm.myAudioContext = new webkitAudioContext();
    //     } 
    //     else {
    //         //alert('Your browser does not support yet Web Audio API');
    //         return;
    //     }
    //     MAlarm.mySource = MAlarm.myAudioContext.createBufferSource();
    //     MAlarm.mySource.buffer = MAlarm.myBuffer;
    //     MAlarm.mySource.connect(MAlarm.myAudioContext.destination);
    //     if ('AudioContext' in window) {
    //         MAlarm.mySource.start(0);
    //     } else if ('webkitAudioContext' in window) {
    //         MAlarm.mySource.noteOn(0);
    //     } 
    // },
    stop: function(){
        if(navigator.userAgent.indexOf("Safari")>-1 && navigator.userAgent.indexOf("Safari")>-1){
            //safari
            MAlarm._playBase64();
        }
        else{
           document.getElementById("alarm-audio").stop();
        }
    }
}




//http://github.hubspot.com/messenger/docs/welcome/


Messenger.options = {
    extraClasses: 'messenger-fixed messenger-on-bottom',
    theme: 'flat'
}

