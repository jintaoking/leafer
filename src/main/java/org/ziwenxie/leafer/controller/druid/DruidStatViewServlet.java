package org.ziwenxie.leafer.controller.druid;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name="loginUsername",value="admin"),
                @WebInitParam(name="loginPassword",value="admin"),
                @WebInitParam(name="resetEnable",value="false")
        })
public class DruidStatViewServlet extends StatViewServlet {

}
