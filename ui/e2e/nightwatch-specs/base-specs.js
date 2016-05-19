TestConfig = require('./_test-config');

module.exports = {
    '@tags': ['HOME'],
    'Ensure presence and visibility of base containers' : function (browser) {
        browser
            .url(TestConfig.url)
            .maximizeWindow()
            .deleteCookies(function() {
                // do something more in here
            })
            .waitForElementVisible('body', TestConfig.defaultTimeout)
            .waitForElementPresent('div#app', TestConfig.defaultTimeout)
            .waitForElementPresent('nav#navBar', TestConfig.defaultTimeout)
            .waitForElementVisible('div.container', TestConfig.defaultTimeout)
            .end();
    }
};