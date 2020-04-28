<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Post editor
</a>

<div class="collapse <#if post??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group col-md-6">
                <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"
                       value="<#if post??>${post.tag}</#if>" name="tag" placeholder="Title" required>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="form-group col-md-6">
                <textarea type="text" class="form-control ${(textError??)?string('is-invalid','')}"
                       value="<#if post??>${post.text}</#if>" name="text" rows=5 cols=20 maxlength=250 placeholder="Post description" required><#if post??>${post.text}</#if></textarea>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group col-md-6">
                <div class="custom-file">
                    <input type="file" name="file" class="custom-file-input" id="customFile">
                    <label class="custom-file-label" for="customFile"><#if post??>${post.filename}<#else>Choose file</#if></label>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" name="id" value="<#if post??>${post.id}</#if>" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save post</button>
            </div>
        </form>
    </div>
</div>