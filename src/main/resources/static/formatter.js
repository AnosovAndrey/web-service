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

 $.fn.outputEdit = function(){
      this.html(this.html()
          .replace(/\n/g,'<br/>')
          .replace('Errors:', '<p class="text-danger">Errors:</p>'));
      return this;
  }

$(document).ready(function() {
    $('.formatted-text').each(function( index ) {
        $(this).multiline();
    });

    $('.formatted-output').outputEdit();
});

