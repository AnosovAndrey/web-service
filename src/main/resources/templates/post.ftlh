<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.page>

<div class="card bg-light mb-3">
    <div class="card-body">
        <h2 class="card-title"><a href="/post/${post.id}">${post.tag}</a></h2>
        <p class="card-text">
            <small class="text-muted">Created by</small>
            <a class="card-text" href="/user-posts/${post.author.id}">${post.authorName}</a>
        </p>
        <p class="card-text">${post.text}</p>
        <#if post.filename??>
            <#if !(post.filename?ends_with("jpg") || post.filename?ends_with("png") || post.filename?ends_with("PNG"))>
                <a href="/img/${post.filename}" download>${post.filename}</a>
                <#if isCurrentUser>
                    <a class="ml-3"><#include "parts/codeCompile.ftlh" /></a>
                </#if>
            <#else>
                <img src="/img/${post.filename}" class="card-img-top">
            </#if>
        </#if>
        <#if post.output??>
            <p class="text-secondary mt-3" id="compile-output">Compile output: ${post.output}</p>
        </#if>
    </div>
    <#if isCurrentUser>
        <div class="card-footer bg-transparent">
            <div class="list-group">
                <a href="/user-posts/${post.author.id}?post=${post.id}">
                    Edit post
                </a>
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