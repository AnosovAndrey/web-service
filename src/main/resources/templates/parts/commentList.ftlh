<#include "security.ftlh">
<#import "pager.ftlh" as p>

<@p.pager url page />

<div class="container">
    <#list page.content as comment>
        <div class="card my-3">
            <div class="card-body">
                <p class="card-text">${comment.text}</p>
                <#if comment.filename??>
                    <#if !(comment.filename?ends_with("jpg") || comment.filename?ends_with("png") || comment.filename?ends_with("PNG"))>
                        <a href="/img/${comment.filename}" download>${comment.filename}</a>
                    <#else>
                        <img src="/img/${comment.filename}" class="card-img-top">
                    </#if>
                </#if>
            </div>
            <div class="card-footer text-muted container">
                <div class="list-group">
                    <a href="/user-posts/${comment.author.id}">
                        Comment by ${comment.authorName}
                    </a>
                    <#if isAdmin>
                        <a href="/deleteComment/${comment.id}">
                            Delete comment
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No comments
    </#list>
</div>