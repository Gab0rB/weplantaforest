var webpack = require('webpack');
var path = require('path');
var rootPath = process.cwd();
var webappPath = path.join(rootPath, 'src', 'main');
var webappSourcePath = path.join(webappPath, 'webapp');
var webappOutputPath = path.join(webappPath, 'resources', 'static');
var HtmlWebpackPlugin = require('html-webpack-plugin');

var LiveReloadPlugin = require('webpack-livereload-plugin');

var isDevMode = typeof process.env.NODE_ENV !== 'undefined' && process.env.NODE_ENV === 'development';

function generateConfig() {
    "use strict";

    var htmlMinifyOptions = {
        removeComments: true,
        collapseWhitespace: true,
        collapseBooleanAttributes: true,
        removeAttributeQuotes: true,
        removeRedundantAttributes: true
    };

    var htmlPluginConfig = {
        title: 'I Plant A Tree',
        template: path.join(webappSourcePath, 'index.tmp'),
        inject: 'body',
        minify: isDevMode ? false : htmlMinifyOptions,
        livereload: isDevMode
    };

    var webpackConfig = {
        context: webappPath,
        entry: {
            'main': './webapp/main'
        },
        output: {
            path: webappOutputPath,
            filename: isDevMode ? 'webapp/[name].js' : 'webapp/[name]-[hash].js'
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
        plugins: [
            new HtmlWebpackPlugin(htmlPluginConfig)
        ]
    };

    if(isDevMode) {
        webpackConfig.plugins.push(
            new LiveReloadPlugin()
        )
    }

    return webpackConfig;
}

module.exports = generateConfig();
