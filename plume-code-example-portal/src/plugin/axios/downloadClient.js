import axios from 'axios'

const instance = axios.create()

function getBaseUrl() {
    if (process.env.NODE_ENV === 'production') {
        return location.origin;
    } else {
        return process.env.VUE_APP_API
    }
}

instance.defaults.timeout = 20000 // 请求超时时间
instance.defaults.baseURL = getBaseUrl()// 请求超时时间
instance.defaults.withCredentials = true
instance.defaults.headers.post['Content-Type'] =
    'application/json;charset=UTF-8'

instance.interceptors.request.use(config => {
    config.responseType = 'blob'
    return config
})
instance.interceptors.response.use(
    response => {
        let blob = new Blob([response.data], {type: 'application/ms-txt.numberformat:@;charset=UTF-8'});
        if (window.navigator.msSaveBlob) {
            window.navigator.msSaveBlob(blob);
        } else {
            let a = document.createElement('a');

            let href = window.URL.createObjectURL(blob); // 创建链接对象

            a.href = href;

            let fileName = localStorage.getItem('fileName');
            if(fileName == null || fileName == undefined){
                fileName = "download.csv";
            }
            a.download = fileName;// 自定义文件名

            document.body.appendChild(a);

            a.click();

            window.URL.revokeObjectURL(href);//移除链接对象

            document.body.removeChild(a); // 移除a元素
        }
    },
    error => Promise.reject(error)
)
export default instance
