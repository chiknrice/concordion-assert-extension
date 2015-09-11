/**
 * Created by Ian Bondoc on 28/08/15.
 */

function showSource(span, div) {
    var pos = span.position();
    div.css('top', pos.top);
    div.css('left', pos.left + 10 + span.width());
    div.show();
}

var script = document.createElement('script');
script.src = 'https://code.jquery.com/jquery-2.1.4.min.js';
script.type = 'text/javascript';
var headTag = document.getElementsByTagName('head')[0];
var firstScriptTag = headTag.getElementsByTagName('script')[0];
headTag.insertBefore(script, firstScriptTag);

window.onload = function () {
    $('span[data-source]').each(function () {
        var sourceDivId = $(this).attr('data-source')
        $('div#' + sourceDivId).hide()
        $(this).hover(function () {
            showSource($(this), $('div#' + sourceDivId))
        }, function () {
            $('div#' + sourceDivId).hide()
        })
    })
}