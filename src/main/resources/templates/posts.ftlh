<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>

<div class="form-row">
    <div class="form-group col-md-6">
        <form method="get" action="/posts" class="form-inline">
            <input type="text" name="filter" class="form-control" value = "${filter!}" placeholder="Search by title">
            <button type="submit" class="btn btn-primary ml-2">Search</button>
        </form>
    </div>
</div>

<#if user??&&name!="Guest">
    <#include "parts/postEdit.ftlh" />
</#if>

<#include "parts/postList.ftlh" />
</@c.page>