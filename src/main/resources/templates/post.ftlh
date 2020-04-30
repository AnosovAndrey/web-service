<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.page>

<div class="card bg-light mb-3">
    <div class="card-body">
        <h2 class="card-title"><a href="/post/${post.id}">${post.title}</a></h2>
        <#if post.tag??>
            <button type="button" class="btn btn-outline-secondary btn-sm" formaction="/tag/${post.tag}">${post.tag}</button>
        </#if>
        <p class="card-text">
            <small class="text-muted">Created by</small>
            <a class="card-text" href="/user-posts/${post.author.id}">${post.authorName}</a>
        </p>
        <p class="card-text">${post.text}</p>
        <#if post.filename??>
            <#if !(post.filename?ends_with("jpg") || post.filename?ends_with("png") || post.filename?ends_with("PNG"))>
                <a href="/img/${post.filename}" download>${post.filename?keep_after(".")}</a>
                <#if isCurrentUser>
                    <a class="ml-3"><#include "parts/codeCompile.ftlh" /></a>
                </#if>
            <#else>
                <img style="width: 400px; height: 400px;" src="/img/${post.filename}" class="card-img-top">
            </#if>
        </#if>
    <p class="text-secondary mt-3" id="compile-output"><#if post.output??>Compile output: ${post.output}</#if></p>
    </div>
    <#if isCurrentUser || isAdmin>
        <div class="card-footer bg-transparent">
            <div class="list-group">
                <#if isCurrentUser>
                    <a href="/user-posts/${post.author.id}?post=${post.id}">
                        Edit post
                    </a>
                </#if>
                <#if isAdmin>
                    <a href="/deletePost/${post.id}">
                        Delete post
                    </a>
                </#if>
            </div>
        </div>
    </#if>
</div>
<script type="text/javascript" src="/static/main.js"></script>


<h2>Comments:</h2>

<#include "parts/addComment.ftlh" />

<#include "parts/commentList.ftlh" />

</@c.page>