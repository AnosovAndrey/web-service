<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    <#if post??>Post edit<#else>Add post</#if>
</a>

<div class="collapse <#if post??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group col-md-6">
                <input type="text" class="form-control ${(titleError??)?string('is-invalid', '')}"
                       value="<#if post??>${post.title}</#if>" name="title" placeholder="Title" required>
                <#if titleError??>
                <div class="invalid-feedback">
                    ${titleError}
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
                    <label class="custom-file-label" for="customFile"><#if post??><#if post.filename??>${post.filename?keep_after(".")}<#else>Choose file</#if><#else>Choose file</#if></label>
                </div>
            </div>
            <div class="form-group col-md-2">
                <select class="custom-select" name="tag" id="tag-form-select">
                    <option value="">Add tag...</option>
                    <option value="Python">Python</option>
                    <option value="Bash">Bash</option>
                    <option value="C/C++">C/C++</option>
                    <option value="tag 1">tag 1</option>
                </select>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" name="id" value="<#if post??>${post.id}</#if>" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Save post</button>
            </div>
        </form>
    </div>
</div>