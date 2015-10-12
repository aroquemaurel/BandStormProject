<%@ page import="bandstorm.User" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>



<div class="container" style="padding-top: 100px;">

    <div class="container">

        <div class="row">

            <div class="col-sm-8 blog-main">

                <div class="panel panel-default">
                    <div class="panel-body">
                        <textarea class="form-control" style="height:60px;"
                                  placeholder="Partagez votre actualité."></textarea>

                        <div style="text-align:right;padding-top:5px;"><button type="button"
                                                                               class="btn btn-default btn-xs">Publier</button>
                        </div>
                    </div>
                </div>

                <div style="background-color:rgb(255,255,255);padding:15px;">

                    <ul class="nav nav-tabs">
                        <li role="presentation" style="width:160px;text-align:center;" class="active"><a
                                href="#">Actualité</a></li>
                        <li role="presentation" style="width:160px;text-align:center;"><a href="#">Abonnement <span
                                class="badge">42</span></a></li>
                        <li role="presentation" style="width:160px;text-align:center;"><a href="#">Abonnés <span
                                class="badge">492</span></a></li>
                    </ul>


                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="${resource(dir: 'images', file: 'r.jpg')}"
                                     data-holder-rendered="true" style="width: 64px; height: 64px;">

                            </a>
                        </div>

                        <div class="media-body">
                            <h4 class="media-heading">Crazy <g:fieldValue bean="${user}" field="username"/></h4>
                            <i>Posté le 16/11/2015</i>
                        </div>
                        <br>
                    </div>

                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-fire"
                                                                               aria-hidden="true"></span> Light (5)
                    </button>
                    <br><br>
                    <blockquote>
                        <p>Curabitur blandit tempus porttitor. <strong>Nullam quis risus eget urna mollis</strong> ornare vel eu leo. Nullam id dolor id nibh ultricies vehicula ut id elit.
                        </p>
                    </blockquote>

                </BR>



                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="${resource(dir: 'images', file: 'a.jpg')}"
                                     data-holder-rendered="true" style="width: 64px; height: 64px;">
                            </a>
                        </div>

                        <div class="media-body">
                            <h4 class="media-heading">Golum</h4>
                            <i>Posté le 16/11/2015</i>
                        </div>
                        <br>
                    </div>
                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"
                                                                               aria-hidden="true"></span> Suivre
                    </button>
                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-fire"
                                                                               aria-hidden="true"></span> Light (5098)
                    </button>
                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-headphones"
                                                                               aria-hidden="true"></span> Participer (176)
                    </button>

                    <h4>Concert organisé le 12/11/2012 par <a>Bob Marley</a></h4>
                    <img src="${resource(dir: 'images', file: 'I.jpg')}" style="width:100%;">
                    <blockquote>

                        <p>Curabitur blandit tempus porttitor. <strong>Nullam quis risus eget urna mollis</strong> ornare vel eu leo. Nullam id dolor id nibh ultricies vehicula ut id elit.
                        </p>
                    </blockquote>
                </BR>

                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-object" data-src="holder.js/64x64" alt="64x64" src="${resource(dir: 'images', file: 'a.jpg')}"
                                     data-holder-rendered="true" style="width: 64px; height: 64px;">
                            </a>
                        </div>

                        <div class="media-body">
                            <h4 class="media-heading">Golum</h4>
                            <i>Posté le 16/11/2015</i>
                        </div>
                        <br>
                    </div>
                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-eye-open"
                                                                               aria-hidden="true"></span> Suivre
                    </button>
                    <button type="button" class="btn btn-default btn-xs"><span class="glyphicon glyphicon-fire"
                                                                               aria-hidden="true"></span> Light (5098)
                    </button><br><br>
                    <blockquote>
                        <p>Curabitur blandit tempus porttitor. <strong>Nullam quis risus eget urna mollis</strong> ornare vel eu leo. Nullam id dolor id nibh ultricies vehicula ut id elit.
                        </p>
                    </blockquote>
                </BR></BR>

                </div><!-- /.blog-userHomePage -->
            </div><!-- /.blog-userHomePage -->

            <div class="col-sm-3  blog-sidebar" style="background-color:rgb(255,255,255);padding:15px;">
                <div class="sidebar-module sidebar-module-inset">
                    <img src="${resource(dir: 'images', file: 'r.jpg')}" data-holder-rendered="true" style="width: 75%;"
                         style="display:inline-block !important;">

                    <h4>Crazy <g:fieldValue bean="${user}" field="username"/></h4>

                    <p> <em>"Merry"</em> Christmas, May the season be fun joyful and especialy crazy!! :P<br><br>

                    </p>

                </div>

                <div class="sidebar-module">
                    <h4>Derniers événements</h4>
                    <ol class="list-unstyled">
                        <li><a href="#">[16 Mars 2013] Concert a roterdam</a></li>
                        <li><a href="#">[16 Mars 2013] Concert a roterdam</a></li>
                        <li><a href="#">[16 Mars 2013] Concert a roterdam</a></li>
                    </ol>
                </div>
                <bR>

                <div class="sidebar-module">
                    <h4>Elsewhere</h4>
                    <ol class="list-unstyled">

                        <li><a href="#">https://twitter.com/Crazy_<g:fieldValue bean="${user}" field="username"/></a></li>
                        <li><a href="#">https://facebook.com/Crazy_<g:fieldValue bean="${user}" field="username"/></a></li>
                    </ol>
                </div>
            </div><!-- /.blog-sidebar -->

        </div><!-- /.row -->

    </div>

</div>

</body>
</html>
