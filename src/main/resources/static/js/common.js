"use strict";

function checkArticleIsExisted() {
    if ($("#articleId").val() == 0) {
        insertArticle();
    }
}


// insert a article into database
function insertArticle() {
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/article/new",
        timeout: 600000,
        async: false,
        success: function (articleId) {
            $("#articleId").val(articleId);
        },
        error: function (e) {
            console.log(e);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


// first insert a tag, then insert an articleTag, last append button in dom
function addTag() {
    $('#addTag').editable({
        mode: "inline",
        type: "text",
        pk: 1,
        url: "/tag/n",
        placeholder: "add a tag",
        ajaxOptions: {
            beforeSend: function (xhr) {
                let header = $("meta[name='_csrf_header']").attr("content");
                let token = $("meta[name='_csrf']").attr("content");
                xhr.setRequestHeader(header, token);
            }
        },
        success: function (tag) {
            checkArticleIsExisted();
            insertArticleTag($("#articleId").val(), tag["id"]);
            createButton(tag["id"], tag["name"]);
        },
        error: function (e) {
            console.log(e);
        },
        display: function (value, response) {
            return false;   //disable this method
        }
    });
}


function insertArticleTag(articleId, tagId) {
    let articleTag = {"articleId": articleId, "tagId": tagId};
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/articleTag/new",
        data: JSON.stringify(articleTag),
        dataType: "json",
        timeout: 600000,
        success: function (articleTag) {
            console.log(articleTag);
        },
        error: function (e) {
            console.log(e);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


function deleteArticleTag(articleId, tagId) {
    let articleTag = {"articleId": articleId, "tagId": tagId};
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/articleTag/delete",
        data: JSON.stringify(articleTag),
        dataType: "json",
        timeout: 600000,
        success: function (articleTag) {
            console.log(articleTag);
        },
        error: function (e) {
            console.log(e);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


var flag = false;


function updateArticle() {
    if (!flag) {
        return;
    }
    checkArticleIsExisted();

    let form = $('#articleForm')[0];
    let data = new FormData(form);

    $("#saveArticle").prop("disabled", true);
    $.ajax({
        type: "POST",
        url: "/article/update",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log("SUCCESS : ", data);
            $("#saveArticle").prop("disabled", false);
            flag = false;

        },
        error: function (e) {
            console.log("ERROR : ", e);
            $("#saveArticle").prop("disabled", false);

        }
    });
}


function articleDelete(articleId) {
    $.ajax({
        type: "post",
        url: "/article/delete/" + articleId,
        timeout: 600000,
        success: function () {
            window.location.href = "/";
        },
        error: function (e) {
            console.log(e);
            window.location.href = "/";
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


function createButton(tagId, tagName) {
    let button = `<button class="btn btn-default btn-sm tagButton">
                    <a href="/tag/"` + tagId + `>` + tagName + `</a>
                    <a class="removeArticleTag" href="#" >
                        <span class="glyphicon glyphicon glyphicon-trash"></span>
                    </a>
                    <span class="tagId" hidden="hidden">` + tagId + `</span>
                    </button>&nbsp;`;
    $("#addTag").before(button);
}


function tagDelete(tagId) {
    $.ajax({
        type: "post",
        url: "/tag/delete/" + tagId,
        timeout: 600000,
        success: function () {
            window.location.href = "/";
        },
        error: function (e) {
            console.log(e);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


function getAllTags() {
    $.ajax({
        type: "post",
        url: "/tag/all/",
        timeout: 600000,
        success: function (tags) {
            displayAllTags(tags)
        },
        error: function (e) {
            console.log(e);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        }
    });
}


function displayAllTags(tags) {
    let template = `<div id="wrapper"><div id="sidebar-wrapper">
            <ul class="sidebar-nav">
                <li class="sidebar-brand">
                    <a href="#" class="allNotes">All Notes</a>
                </li>`;

    $.each(tags, function (index, tag) {
        template += `<li>
                    <span style="display: none;">` + tag["id"] + `</span>
                    <a href="#" class="changeTag">
                    <span>` + tag["name"] + "  (" + tag["articles"].length + `)</span>
                    </a></li>`;
    });
    template += '</ul></div></div>';

    $(".navbar").after(template);

    $("#sidebar-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });

    $(".changeTag").click(function () {
        let tagId = $(this).prev().text();
        getOneTag(tagId);
    });

    $(".allNotes").click(function (event) {
        paginationArticle(1);
    });
}


function paginationArticle(page) {
    $.ajax({
        type: "post",
        url: "/page/" + page,
        dataType: "json",
        contentType: "application/json",
        success: function (articles) {
            displayArticles(articles);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        },
        error: function (e) {
            console.log(e);
        }
    });
}


function getOneTag(tagId) {
    $.ajax({
        type: "post",
        url: "/tag/" + tagId,
        dataType: "json",
        contentType: "application/json",
        success: function (tag) {
            console.log(tag);
            displayArticles(tag["articles"]);
        },
        // add csrf header and token
        beforeSend: function (xhr) {
            let header = $("meta[name='_csrf_header']").attr("content");
            let token = $("meta[name='_csrf']").attr("content");
            xhr.setRequestHeader(header, token);
        },
        error: function (e) {
            console.log(e);
        }
    });
}


// display articles of a tag or a page in index.html
function displayArticles(articles) {
    $(".article").remove();

    $.each(articles, function (index, article) {

        // get article title
        let title = article["title"];
        if (title == null) {
            title = "Untitled";
        }

        // get article body
        let body = article["body"];
        if (body == null) {
            body = "";
        } else if (body.length > 100) {
            body = body.substring(0, 100) + "...";
        }

        // display article
        var template = `<section class="article"><div class="articleInfo"><a class="articleTitle" href="/article/`
            + article["id"] + `">` + title + `</a><span class="createdTime">`
            + new Date(article["createdTime"]).Format("yy-MM-dd hh:mm") +
            `</span></div><div class="articleBody">` + body + `</div><div style="margin-top: 10px;">`;

        // display article tag
        $.each(article['tags'], function (index, tag) {
            template += `<span style="display: none;">` + tag["id"] +
                `</span><a href="#" class="changeTag" style="color: #888888;text-decoration: none"># <span>`
                + tag['name'] + `</span>&nbsp;&nbsp;</a>`;
        });

        template += `</div><hr/></section>`;

        $("#main").append(template);
    });
}


Date.prototype.Format = function (fmt) {
    let o = {
        "y+": this.getFullYear(),
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S+": this.getMilliseconds()
    };
    for (let k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            if (k == "y+") {
                fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
            } else if (k == "S+") {
                let lens = RegExp.$1.length;
                lens = (lens == 1 ? 3 : lens);
                fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1, lens));
            } else {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
    }
    return fmt;
};
