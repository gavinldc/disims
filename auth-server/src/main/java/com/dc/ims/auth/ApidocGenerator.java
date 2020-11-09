package com.dc.ims.auth;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * Created by lvdanchen on 2020/10/29.
 */
public class ApidocGenerator {


    public static void main(String[] args){


        DocsConfig config = new DocsConfig();
        config.setProjectPath("/Users/lvdanchen/git/disims/auth-server"); // 项目根目录
        config.setProjectName("auth-server"); // 项目名称
        config.setApiVersion("V1.0");       // 声明该API的版本
        config.setDocsPath("/Users/lvdanchen/git/disims/auth-server"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        //docsConfig.addPlugin(new MarkdownDocPlugin());
        Docs.buildHtmlDocs(config); // 执行生成文档


    }

}
