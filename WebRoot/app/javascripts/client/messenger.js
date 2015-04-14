//http://github.hubspot.com/messenger/docs/welcome/


Messenger.options = {
    extraClasses: 'messenger-fixed messenger-on-bottom',
    theme: 'flat'
}

Messenger().post({
  message: '<a href="www.baidu.com">您已成功退出天下会！</a>',
  type: 'success',
  showCloseButton: true
});
console.log('messenger')