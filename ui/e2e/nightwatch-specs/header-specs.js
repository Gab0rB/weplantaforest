TestConfig = require('./_test-config');

module.exports = {
    '@tags': ['HEADER'],
    'Ensure presence and visibility of header containers' : function (browser) {
        browser
            .url(TestConfig.url)
            .maximizeWindow()
            .deleteCookies(function() {
                // do something more in here
            })
            .waitForElementVisible('body', TestConfig.defaultTimeout)
            .waitForElementVisible('div.container', TestConfig.defaultTimeout)
            .waitForElementPresent('nav.navbar', TestConfig.defaultTimeout)
            .waitForElementPresent('div.navbar-header', TestConfig.defaultTimeout)
            .waitForElementPresent('div.navbar-collapse', TestConfig.defaultTimeout)
            .end();
    }
};