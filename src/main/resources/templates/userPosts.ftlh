<#import "parts/common.ftlh" as c>

<@c.page>
<h3>${userChannel.username}</h3>
<#if !isCurrentUser>
    <#if isSubscriber>
        <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
    <#else>
        <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
    </#if>
</#if>
<div class="btn-group">
    <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        Info
    </button>
    <div class="dropdown-menu w-400">
        <div class="container">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <div class="card-title">Subscriptions</div>
                            <h3 class="card-text">
                                <a href="/user/subscriptions/${userChannel.id}/list">${subscriptionsCount}</a>
                            </h3>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <div class="card-title">Subscribers</div>
                            <h3 class="card-text">
                                <a href="/user/subscribers/${userChannel.id}/list">${subscribersCount}</a>
                            </h3>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#if isCurrentUser>
    <#include "parts/postEdit.ftlh" />
</#if>

<#include "parts/postList.ftlh" />
</@c.page>