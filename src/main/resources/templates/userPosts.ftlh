<#import "parts/common.ftlh" as c>

<@c.page>
<div class="row">
    <div class="col-sm-auto"><h3>${userChannel.username}</h3></div>
    <div class="col-sm-auto col-ml-auto">
        <a class="btn btn-link" data-toggle="collapse" href="#collapsedUserInfo" role="button" aria-expanded="false" aria-controls="collapsedUserInfo">
            info
        </a>
    </div>
</div>
<div class="collapse mb-2" id="collapsedUserInfo">
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
<#if !isCurrentUser>
    <#if isSubscriber>
        <a class="btn btn-info" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
    <#else>
        <a class="btn btn-info" href="/user/subscribe/${userChannel.id}">Subscribe</a>
    </#if>
</#if>


<#if isCurrentUser>
    <#include "parts/postEdit.ftlh" />
</#if>

<#include "parts/postList.ftlh" />
</@c.page>