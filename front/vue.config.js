const path = require('path'); // node의 path 모듈
module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: "http://localhost:8080",
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    },
    // 웹팩 설정
    configureWebpack: {
        resolve: {  // resolve.alias 는 별칭을 사용해 모듈을 더 쉽게 import, export 할 수 있게 해줌
            alias: {
                // '~'는 현재 프로젝트의 /src 까지의 경로를 의미한다.
                '~': path.join(__dirname, '/src')
            }
        }
    },

    transpileDependencies: [
      'vuetify'
    ]
}
