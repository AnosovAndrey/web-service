<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Service</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/posts">Posts</span></a>
            </li>
            <#if user??>
            <li class="nav-item">
                <a class="nav-link" href="/user-posts/${currentUserId}">My posts</span></a>
            </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">User List</span></a>
                </li>
            </#if>
            <#if user??&&name!="Guest">
                <li class="nav-item">
                    <a class="nav-link" href="/user/profile">Profile</span></a>
                </li>
            </#if>
        </ul>
        <div class="navbar-text mr-3"><#if user??>${name}<#else>Please login</#if></div>
        <@l.logout/>
    </div>
</nav>