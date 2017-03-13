var UTM = UTM || {};

AJS.toInit(function() {
    var $ = AJS.$;

    UTM.initExportDialog();
    UTM.initImportDialog();

    $('#utm-export-menu-link').click(function() {
        AJS.dialog2("#utm-export-dialog").show();
    });

    $('#utm-import-menu-link').click(function() {
        AJS.dialog2("#utm-import-dialog").show();
    });
});

UTM.initExportDialog = function() {
    var exportDialog = "<!-- Render the dialog -->" +
                       "<section role=\"dialog\" id=\"utm-export-dialog\" class=\"aui-layer aui-dialog2 aui-dialog2-medium\" aria-hidden=\"true\">" +
                           "<!-- Dialog header -->" +
                           "<header class=\"aui-dialog2-header\">" +
                               "<!-- The dialog's title -->" +
                               "<h2 class=\"aui-dialog2-header-main\">" +
                                   "<img src=\"" + AJS.params.contextPath + "/download/resources/eps.focuspro.UiTestMinion/images/utm-logo.png\" alt=\"Minion Logo\" style=\"height: 36px; padding-right: 15px; margin-bottom:-10px;\">" +
                                   "Minion Export" +
                               "</h2>" +
                               "<!-- Close icon -->" +
                               "<a class=\"aui-dialog2-header-close\">" +
                                   "<span class=\"aui-icon aui-icon-small aui-iconfont-close-dialog\">Close</span>" +
                               "</a>" +
                           "</header>" +
                           "<!-- Main dialog content -->" +
                           "<div class=\"aui-dialog2-content\" style=\"min-height: 80px;\">" +
                               "<form class=\"aui top-label\" action=\"#\">" +
                                   "<div class=\"field-group\">" +
                                       "<label for=\"comment-email\">Export test data to project:<span class=\"aui-icon icon-required\">(required)</span></label>" +
                                       "<input id=\"export-test-data-root-path-input\" class=\"text long-field\" type=\"text\" placeholder=\"C:\\Project\\EPS_Conf_Addons\\trunk\\Develop\\PageTreeCreator\">" +
                                       "<div class=\"description\">Note that all existing test files will be overridden</div>" +
                                   "</div>" +
                               "</form>" +
                           "</div>" +
                           "<footer class=\"aui-dialog2-footer\">" +
                               "<div class=\"aui-dialog2-footer-actions\" >" +
                                   "<button id=\"utm-export-dialog-button\" class=\"aui-button aui-button-primary\" disabled>Export</button>" +
                                "</div>" +
                           "</footer>" +
                       "</section>";

    if (AJS.$("#utm-export-dialog").length === 0) {
        AJS.$(exportDialog).insertAfter('#main-content');

        $('#utm-export-dialog-button').click(function() {
            var $testDataRootPathInput = $('#export-test-data-root-path-input');
            var temp = $testDataRootPathInput.val();
            UTM.doExport($testDataRootPathInput.val());
            $testDataRootPathInput.val('');
        });

        $('#export-test-data-root-path-input').on('input change',function() {
            $('#utm-export-dialog-button').prop('disabled', !$(this).val().length);
        });
    }
};

UTM.initImportDialog = function() {
    var importDialog = "<!-- Render the dialog -->" +
                       "<section role=\"dialog\" id=\"utm-import-dialog\" class=\"aui-layer aui-dialog2 aui-dialog2-medium\" aria-hidden=\"true\">" +
                           "<!-- Dialog header -->" +
                           "<header class=\"aui-dialog2-header\">" +
                               "<!-- The dialog's title -->" +
                               "<h2 class=\"aui-dialog2-header-main\">" +
                                   "<img src=\"" + AJS.params.contextPath + "/download/resources/eps.focuspro.UiTestMinion/images/utm-logo.png\" alt=\"Minion Logo\" style=\"height: 36px; padding-right: 15px; margin-bottom:-10px;\">" +
                                   "Minion Import" +
                               "</h2>" +
                               "<!-- Close icon -->" +
                               "<a class=\"aui-dialog2-header-close\">" +
                                   "<span class=\"aui-icon aui-icon-small aui-iconfont-close-dialog\">Close</span>" +
                               "</a>" +
                           "</header>" +
                           "<!-- Main dialog content -->" +
                           "<div class=\"aui-dialog2-content\" style=\"min-height: 80px;\">" +
                               "<form class=\"aui top-label\" action=\"#\">" +
                                   "<div class=\"field-group\">" +
                                       "<label for=\"comment-email\"><br>Import test data from project:<span class=\"aui-icon icon-required\">(required)</span></label>" +
                                       "<input id=\"import-test-data-root-path-input\" class=\"text long-field\" type=\"text\" placeholder=\"C:\\Project\\EPS_Conf_Addons\\trunk\\Develop\\PageTreeCreator\">" +
                                       "<div class=\"description\">Note that all existing spaces for ui tests will be overridden</div>" +
                                   "</div>" +
                               "</form>" +
                           "</div>" +
                           "<footer class=\"aui-dialog2-footer\">" +
                               "<div class=\"aui-dialog2-footer-actions\" >" +
                                   "<button id=\"utm-import-dialog-button\" class=\"aui-button aui-button-primary\" disabled>Import</button>" +
                                "</div>" +
                           "</footer>" +
                       "</section>";

    if (AJS.$("#utm-import-dialog").length === 0) {
        AJS.$(importDialog).insertAfter('#main-content');

        $('#utm-import-dialog-button').click(function() {
            var $testDataRootPathInput = $('#import-test-data-root-path-input');
            var temp = $testDataRootPathInput.val();
            UTM.doImport($testDataRootPathInput.val());
            $testDataRootPathInput.val('');
        });

        $('#import-test-data-root-path-input').on('input change',function() {
            $('#utm-import-dialog-button').prop('disabled', !$(this).val().length);
        });
    }
};

UTM.doExport = function(testDataRootPath) {
    var url = AJS.params.contextPath + '/rest/utm/1.0/minion/export?testDataRootPath=' + testDataRootPath;
    console.log('url', url);
    UTM.ajaxGet(url);
};

UTM.doImport = function(testDataRootPath) {
    var url = AJS.params.contextPath + '/rest/utm/1.0/minion/import?testDataRootPath=' + testDataRootPath;
    console.log('url', url);
    UTM.ajaxGet(url);
};


UTM.ajaxGet = function(url) {
    var $ = AJS.$;
    var RESP_TYPE = Object.freeze({'SUCCESS': 'SUCCESS', 'REDIRECT': 'REDIRECT', 'UI_ERROR': 'UI_ERROR', 'UNKNOWN_ERROR': 'UNKNOWN_ERROR'});

    var handleSuccessResponse = function(resp) {
        try {
            console.log("UiTestMinion Json Response: ", resp);
            if (resp.type === RESP_TYPE.SUCCESS)
                EPS.Flag.showSuccessFlag(resp.data, 'Success');
            else if (resp.type === RESP_TYPE.REDIRECT)
                window.location = AJS.params.contextPath + resp.data;
            else if (resp.type === RESP_TYPE.UI_ERROR)
                EPS.Flag.showErrorFlag(resp.data, 'Error!');
            else if (resp.type === RESP_TYPE.UNKNOWN_ERROR)
                EPS.Flag.showErrorFlag('An unknown error occurred in Java server code:<br>' + resp.data, 'Unknown error!');
            else
                EPS.Flag.showErrorFlag('Response has an undefined response type. Check the response json!', 'Error!');
        }
        catch (err) {
            console.log('UiTestMinion JavaScript Error: ', err);
            EPS.Flag.showErrorFlag('An unhandled error occurred (see console for details):<br>' + err.message, 'JavaScript error!');
        }
    };

    $.ajax({
        method: 'GET',
        url: url,
        dataType: 'json',
        success: function(resp) {
            handleSuccessResponse(resp);
        },
        error: function(request, status, error) {
            console.log('UiTestMinion Ajax Error', error);
            EPS.Flag.showErrorFlag('An unhandled error occurred (see console for details):<br>' + error.message, 'Ajax error!');
        },
        complete: function() {
            AJS.dialog2("#utm-export-dialog").hide();
            AJS.dialog2("#utm-import-dialog").hide();
        }
    });
};