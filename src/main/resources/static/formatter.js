$.fn.multiline = function(){
    //this.text(text);
    //var newText = this.html().replace(/\n/g,'<br/>');
    //alert(this.html());
    this.html(this.html()
        .replace(/\n/g,'<br/>')
        .replace('&lt;code&gt;', '<pre><code>')
        .replace('&lt;/code&gt;', '</code></pre>')
        .replace('&lt;link&gt;', '<a href="')
        .replace('&lt;/link&gt;', '" class="text-decoration-none">')
        .replace('&lt;link-name&gt;', '')
        .replace('&lt;/link-name&gt;', '</a>'));
    return this;
}

$(document).ready(function() {
    $('.formatted-text').each(function( index ) {
        $(this).multiline();
    });
});

// Now you can do this:
/*$.each([$('.card-text post-text')], function(){
    alert("yo");
  // do a lot of things here
  //var text = this.value;
  //this.multiline();
});*/
//var text = document.getElementById("formatted-text").value;
//$("#formatted-text").multiline(text);
//$("#formatted-text").multiline();

