var EPS = EPS || {};
EPS.Flag = {};
var confluenceFlag = require('aui/flag');
var epsFlag;

EPS.Flag.showErrorFlag = function(message, title) {
    EPS.Flag.showFlag('error', message, title);
};

EPS.Flag.showSuccessFlag = function(message, title) {
    EPS.Flag.showFlag('success', message, title);
};

EPS.Flag.showInfoFlag = function(message, title) {
    EPS.Flag.showFlag('info', message, title);
};

EPS.Flag.showWarningFlag = function(message, title) {
    EPS.Flag.showFlag('warning', message, title);
};

EPS.Flag.showFlag = function(type, message, title) {
    EPS.Flag.closeFlag();

    epsFlag = require('aui/flag')({
        type: type,
        title: title,
        body: '<p> ' + message + ' </p>'
    });
};

EPS.Flag.closeFlag = function() {
    if (epsFlag != null) //is the same as: epsFlag !== undefined || epsFlag !== null
        epsFlag.close();
};