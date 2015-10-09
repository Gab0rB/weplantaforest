var webpack = require('webpack');
var path = require('path');
var rootPath = process.cwd();
var webappPath = path.join(rootPath, 'src', 'main');
var webappSourcePath = path.join(webappPath, 'webapp');
var webappOutputPath = path.join(webappPath, 'resources', 'static', 'webapp');

var LiveReloadPlugin = require('webpack-livereload-plugin');

var isDevMode = typeof process.env.NODE_ENV !== 'undefined' && process.env.NODE_ENV === 'development';

function generateConfig() {
    "use strict";
    var config = {
        context: webappPath,
        entry: ['./webapp/main'],
        output: {
            path: webappOutputPath,
            filename: 'main.js'
        },
        target: 'web',
        module: {
            loaders: [
                { test: /\.js$/, loader: 'babel-loader', exclude: /node_modules/ },
                { test: /\.less$/, loader: 'css!less' },
                { test: /\.(png|jpg|jpeg|gif|svg)$/, loader: 'url-loader?limit=10000' },
                { test: /\.(woff|woff2)$/, loader: 'url-loader?limit=10000' },
                { test: /\.(ttf|eot)$/, loader: 'url-loader?limit=10000' }
            ]
        },
        plugins: []
    };

    if(isDevMode) {
        config.plugins.push(
            new LiveReloadPlugin()
        )
    }

    return config;
}

module.exports = generateConfig();
