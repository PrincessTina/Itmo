$(document).ready(() => {
    var LegendModel = Backbone.Model.extend();

    var LegendCollection = Backbone.Collection.extend({
        model: LegendModel,
        url: 'legends?id=all'
    });

    window.Top = Backbone.View.extend({
        el: $('body'),
        menu: null,

        events: {
            'click ._myVkPage': 'goToMyVkPage'
        },

        initialize() {
            _.bindAll(this, 'render');
            _.bindAll(this, 'paste');

            this.collection = new LegendCollection();
            this.render();

            this.menu = new Menu();

            this.collection.fetch({
                success: () => {
                    this.paste();
                },
                fail: () => {
                    throw "Error in getting news";
                }
            });
        },

        paste() {
            let count = 1;

            this.collection.models.forEach((legend) => {
                if (document.getElementsByClassName("block")[0].children.length < 4) {
                    document.getElementsByClassName("block")[0].innerHTML += `
                    <div class="w3-quarter w3-display-container">
                      <img class="w3-hover-grayscale" src="${legend.attributes.image.link}" alt="Sandwich" style="width:100%">
                      <div class="w3-display-topmiddle w3-text-black w3-xlarge">${legend.attributes.rating}</div>
                      <h3>${count}</h3>
                      <p><a href="legend.html#${legend.attributes.id}">${legend.attributes.name}</a></p>
                    </div>`;
                } else {
                    document.getElementsByClassName("block")[0].classList.remove("block");

                    document.getElementsByClassName("w3-main")[0].innerHTML += `
                    <div class="w3-row-padding w3-padding-16 w3-center block"></div>`;

                    document.getElementsByClassName("block")[0].innerHTML += `
                    <div class="w3-quarter w3-display-container">
                      <img class="w3-hover-grayscale" src="${legend.attributes.image.link}" alt="Sandwich" style="width:100%">
                      <div class="w3-display-topmiddle w3-text-black w3-xlarge">${legend.attributes.rating}</div>
                      <h3>${count}</h3>
                      <p><a href="legend.html#${legend.attributes.id}">${legend.attributes.name}</a></p>
                    </div>`;
                }
                count ++;
            });
        },

        goToMyVkPage() {
            window.open("https://vk.com/kristi_tulpan");
        },

        render() {
            $(this.el).append(`
         <!-- Меню -->
         <div class="menu"></div>
         
         <!-- Новости -->
         <div class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px">
              <h1 class="w3-center">Top-10 of legends</h1>
              <div class="w3-row-padding w3-padding-16 w3-center block"></div>
        </div>
        
        <!-- Футер с контактами -->
          <footer class="w3-container w3-padding-64 w3-center w3-opacity w3-light-grey w3-xlarge">
            <i class="fa fa-vk w3-hover-opacity _myVkPage"></i>   
            <p class="w3-medium"><i>Powered by PrincessTina</i></p>
          </footer>
        `);
        },
    });
});