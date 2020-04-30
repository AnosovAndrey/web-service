<a class="btn btn-outline-secondary btn-sm" data-toggle="collapse" href="#collapseCompileForm" role="button" aria-expanded="false" aria-controls="collapseExample">
    Compilation
</a>

<div class="collapse" id="collapseCompileForm">
    <div class="form-group mt-3">
        <form enctype="multipart/form-data" id="compile-post-form">
            <div class="form-group col-md-6">
                <input class="form-control mb-3 mt-3" type="text" name = "input" id="compile-form-input" placeholder="Your input">
            </div>
            <div class="input-group col-md-6">
                <select class="custom-select" name="compiler" id="compile-form-select" required>
                    <option value="" selected disabled>Choose compiler...</option>
                    <option value="1">Python</option>
                    <option value="2">Bash</option>
                    <option value="3">Ruby</option>
                    <option value="4">Clojure</option>
                    <option value="5">JavaScript</option>
                    <option value="6">Php</option>
                    <option value="7">Scala</option>
                    <option value="8">Go</option>
                    <option value="9">Java</option>
                    <option value="10">VB.NET</option>
                    <option value="11">C/C++</option>
                    <option value="12">C#</option>
                    <option value="13">Perl</option>
                    <option value="14">Rust</option>
                </select>
                <div class="input-group-append">
                    <button type="submit" class="btn btn-outline-secondary" type="button" id="btn-submit-compile">Compile</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="hidden" id="postId" value="${post.id}">
        </form>
    </div>
</div>