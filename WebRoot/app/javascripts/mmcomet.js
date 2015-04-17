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
        document.getElementById('MMComet').onkeydown = function(event) {
            if (event.keyCode == 13) {
                MMComet.sendMessage();
            }
        };
    };

    MMComet.socket.onclose = function () {
        document.getElementById('MMComet').onkeydown = null;
        console.log('Info: WebSocket closed.');
    };

    MMComet.socket.onmessage = function (message) {
        // console.log(message.data);
        MMComet.showMsg(message.data)
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
      message: '<a href="www.baidu.com">'+data+'</a>',
      type: 'success',
      showCloseButton: true
    });
}

window.MMComet = MMComet;


//http://github.hubspot.com/messenger/docs/welcome/


Messenger.options = {
    extraClasses: 'messenger-fixed messenger-on-bottom',
    theme: 'flat'
}

