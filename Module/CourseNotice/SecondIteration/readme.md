# 课表提醒模块说明

## 概述

本模块需要用户的网络权限。如果使用过本软件，用户进入该模块时课表会直接显示。如果没有使用过或者需要更新课表可以点击更新课表的按钮，进入登录教务系统获取课表的页面，更新完成后会返回。

![GetDataFlow](.\img\GetDataFlow.png)

## 主要代码

### 获取验证码

获取验证码及Cookie，成功获取验证码后，通过handler通知UI更新验证码图片。

```java
 private void ChangeImage() {

        Request request = new Request.Builder()
                .url("http://210.42.121.241/servlet/GenImg")
                //.header("Host","210.42.121.241")
                //   .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .build();
        okhttp3.OkHttpClient  okHttpClient=new okhttp3.OkHttpClient();
        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] byte_image =  response.body().bytes();

                //   Handler handler=new Handler();

                //通过handler更新UI
                Message message = handler.obtainMessage();
                message.obj = byte_image;
                message.what = 1;
                Log.i("info_handler","handler");
                handler.sendMessage(message);
                //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
                Headers headers = response.headers();
                Log.d("info_headers", "header " + headers);
                List<String> cookies = headers.values("Set-Cookie");
                String session = cookies.get(0);
                Log.d("info_cookies", "onResponse-size: " + cookies);
                cookie= session.substring(0, session.indexOf(";"));
                Log.i("info_s", "session is  :" + cookie);
            }
        });
    }
```

### 密码加密

通过查看教务系统html代码可以发现，我们输入的密码在http请求中会经过md5加密后传输，所以在请求之前要先准备好加密的密码。

```java
public static String md5(String string) {/*md5加密，用于密码加密*/
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
```

### 登录

用户输入学号密码以及验证码，发送post请求。

```java
private void LoginServer() {
        RequestBody formBody1 = new FormBody.Builder()
                .add("id", mEtUserName.getText().toString())
                .add("pwd",md5(pwd.getText().toString())) pwd.getText().toString()
                .add("xdvfb",randcode.getText().toString())
                .build();
        System.out.println("code:"+randcode.getText().toString());
        System.out.println("code:"+cookie);
        Request request = new Request.Builder()
                .url("http://210.42.121.241/servlet/Login")
                .addHeader("Cookie", cookie)
                .header("Host", "210.42.121.241")
                .header("Referer", "http://210.42.121.241/stu/stu_index.jsp")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                .post(formBody1)
                .build();

        OkHttpClient mOkHttpClient = new OkHttpClient();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("failure");
            }
            Headers headers1;String session1;
            @Override
            public void onResponse(Call call, Response response1) throws IOException {
                byte[] b = response1.body().bytes();
                String temp = new String(b, "GB2312");
                if(response1.isSuccessful()){
                    Log.i("info_call2success",temp);
                }
                get_crsftoken(temp);
            }
        });
    }
```



### 获取课表

相关函数说明：

```
void getViewStateValue(String html)
```

该函数用于从课表请求返回的页面中获取相应信息并存入数据库中。

```java
Request request2 = new Request.Builder()  .url("http://210.42.121.241/"+csrf.substring(0,26)+csrf.substring(45,91)+"&action=normalLsn&year=2019&term=1&state=")
                        .addHeader("Cookie",cookie)//imp_cookie                 
                        .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                        .build();
                OkHttpClient mOkHttpClient2 = new OkHttpClient();
                mOkHttpClient2.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                    }
                    @Override
                    public void onResponse(Call call, Response response2) throws IOException {
                        byte[] b = response2.body().bytes();
                        String temp = new String(b, "GB2312");
                        getViewStateValue(temp);
                    }
                });
```

