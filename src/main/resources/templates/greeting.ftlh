<#include "parts/security.ftlh">
<#import "parts/common.ftlh" as c>

<@c.page>
<#if user??&&name!="Guest">
    <h5>Hello, ${name}!</h5>
<#else>
    <h5>Hello, guest!</h5>
</#if>
<div>Something</div>
</@c.page>
