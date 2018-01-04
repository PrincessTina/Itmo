$(document).ready(function () {
    window.Index = Backbone.View.extend({
        el: $('body'),

        initialize: function () {
            _.bindAll(this, 'render');

            this.render();
        },

        render: function () {
            $(this.el).append(`
        <div>
            <button onclick="new RegWindow()">Зарегистрироваться</button>
        </div>
        `);
        }
    });
});