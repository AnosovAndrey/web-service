<#include "security.ftlh">
<#import "pager.ftlh" as p>

<@p.pager url page />
<div class="container">
    <#list page.content as post>
    <div class="card my-3">
        <div class="card-header">
            <h2 class="card-title"><a href="/post/${post.id}">${post.title}</a></h2>
                <p class="card-text"><small class="text-muted">Created by</small>
                    <a class="card-text" href="/user-posts/${post.author.id}">${post.authorName}</a>
                </p>
                <#if post.author.id == currentUserId>
                    <a href="/user-posts/${post.author.id}?post=${post.id}">
                        Edit
                    </a>
                </#if>
        </div>
        <div class="card-body">
            <p class="card-text">${post.text}</p>
        </div>
        <#if post.tag??>
            <div class="card-footer bg-transparent">
                <a href="/tag/${post.tag}" class="btn btn-secondary btn-sm" role="button" aria-pressed="true">${post.tag}</a>
            </div>
        </#if>
    </div>
    <#else>
        No posts
    </#list>
</div>

<@p.pager url page />