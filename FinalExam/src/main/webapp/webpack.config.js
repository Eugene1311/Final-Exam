'use strict';
const NODE_ENV = process.env.NODE_ENV || 'development';
const webpack = require('webpack');

module.exports = {
    entry: ["webpack-dev-server/client", "webpack/hot/dev-server", "./js/dist/app.js"],

    output: {
        path: __dirname + "/js",
        filename: "build.js"
    },
    
    watch: NODE_ENV == "development",

    watchOptions: {
        aggregateTimeout: 100
    },

    devtool: NODE_ENV == "development" ? "cheap-inline-module-source-map" : null,

    plugins: [
        new webpack.NoErrorsPlugin(),
        new webpack.DefinePlugin({
            NODE_ENV:JSON.stringify(NODE_ENV)
        }),
        new webpack.optimize.DedupePlugin(),
        new webpack.HotModuleReplacementPlugin()
    ],

    resolve: {
        modulesDirectories: ['node_modules'],
        extensions: ['','.js','.jsx']
    },

    resolveLoader: {
        modulesDirectories: ['node_modules'],
        moduleTemplates: ['*-loader', '*'],
        extensions: ['','.js','.jsx']
    },

    module: {
        loaders: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: "babel?presets[]=es2015"
            }
        ]
    },

    devServer: {
        host: 'localhost',
        port: 8000,
        hot: true
    }
}