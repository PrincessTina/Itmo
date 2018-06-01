$(document).ready(() => {
    var NotificationModel = Backbone.Model.extend({
        url: 'notifications'
    });

    window.NotificationChecker = Backbone.View.extend({
        news: null,

        initialize() {
            _.bindAll(this, 'init');
            _.bindAll(this, 'createNotification');

            this.news = new NotificationModel();
        },

        init() {
            this.news.fetch();

            let header = this.news.attributes.type;
            let body = this.news.attributes.body;

            if (header !== undefined && body !== undefined) {
                header = header.toUpperCase();

                this.createNotification(header, body);

                setTimeout(function () {
                    document.getElementsByClassName("notice")[0].remove(0);
                }, 6 * 1000);
            }

            setTimeout(this.init, 10 * 1000);
        },

        createNotification(header, body) {
            $('body').append(`
                <div class="w3-card-4 w3-padding w3-panel w3-light-blue w3-display-container notice" style="position: fixed; top: 30%; 
                right: 0;">
                  <h3>` + header + `</h3>
                  <p>` + body + `</p>
                </div>
            `);
        }
    });
});