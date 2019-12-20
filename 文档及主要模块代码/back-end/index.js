const http = require('http');
const url = require('url');
const fs = require('fs');
const querystring = require('querystring');
let urls = JSON.parse(fs.readFileSync('_url.json', 'utf8'));
module.exports = () => {// api/news/?all
    http.createServer((req, res) => {
        let data = "";
        req.on('data', (e) => { data += e });
        req.on('end', () => {
            console.log(data);
            let path = url.parse(req.url).pathname;
            let query = querystring.parse(url.parse(req.url).query);
            let resText;
            try {
                if (path.startsWith('/api/news/')) {//
                    let resArr = [];
                    if (query['all'] !== undefined || query['lecture'] !== undefined) urls['academic'].forEach((e) => { resArr.push(e) });//返回所有
                    if (query['all'] !== undefined || query['whunews'] !== undefined) urls['news'].forEach((e) => { resArr.push(e) });
                    if (query['all'] !== undefined || query['holiday'] !== undefined) urls['notification'].forEach((e) => { resArr.push(e) });
                    if (query['all'] !== undefined || query['bulletin'] !== undefined) urls['bulletin'].forEach((e) => { resArr.push(e) });
                    if (query['all'] !== undefined || query['sports'] !== undefined) urls['sports'].forEach((e) => { resArr.push(e) });//返回所有
                    if (query['all'] !== undefined || query['movie'] !== undefined) urls['movie'].forEach((e) => { resArr.push(e) });
                    if (query['all'] !== undefined || query['supermarket'] !== undefined) urls['supermarket'].forEach((e) => { resArr.push(e) });
                    if (query['all'] !== undefined || query['library'] !== undefined) urls['library'].forEach((e) => { resArr.push(e) });
                    resText = JSON.stringify(resArr);
                } else if (path.startsWith('/api/msg/')) {//
                    let resArr = JSON.parse(fs.readFileSync('_message.json', 'utf8'));
                    try {
                        let qu =  JSON.parse(JSON.parse(data).Num);
                        resArr  = resArr.filter((e)=>{return qu.includes(e.mid)});
                    } catch (error) {
                        
                    }
                    resText=JSON.stringify(resArr);
                } else {//兼容旧 api
                    resText = url.parse(req.url).pathname === '/' ? fs.readFileSync('url.json', 'utf8') : fs.readFileSync('_message.json', 'utf8');
                }
                res.writeHead(200, { "Content-Type": "application/json" });
                res.write(resText);
                res.end();
            } catch (error) {
                console.log(error);
                res.writeHead(500, {});
                res.write("error");
                res.end();
            }
        });

    }).listen(8888);
}
module.exports();

// var a = [];
// document.querySelectorAll('.p1').forEach((e)=>{let t = e.querySelectorAll('a')[1];
// a.push({"url":t.href,"title":t.text});
// console.log(t)});
// JSON.stringify(a)



// http://tyb.whu.edu.cn/index/tzgg/64.htm
// let t=[];
// document.querySelector('.list ul').querySelectorAll('a').forEach((e)=>{
// t.push({'url':e.href,title: e.textContent});
// })
// JSON.stringify(t);



// http://www.lib.whu.edu.cn/news/
// let t = [];
// document.querySelectorAll('table')[2].querySelectorAll('tr td a').forEach(
//     (e, i) => {
//         if (i % 2 === 0) {
//             let c = { url: e.href, title: e.textContent.trim() };
//             if(c.url.startsWith('javascript')){
//                 c.url = 'http://www.lib.whu.edu.cn/news/view.asp?id='+ /javascript:view\((.*)\)/.exec(c.url)[1];
//             }
//             t.push(c);
//         }
//     }
// )
// JSON.stringify(t);
