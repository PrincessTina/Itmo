$(document).ready(() => {
    var ImageModel = Backbone.Model.extend();

    var ImageCollection = Backbone.Collection.extend({
        model: ImageModel,
        url: 'images?action=get_sl'
    });

    window.IndexSlider = Backbone.View.extend({
        el: null,
        imageCount: 0,

        initialize() {
            this.el = $('.slider');

            _.bindAll(this, 'render');
            _.bindAll(this, 'changeSliderImage');

            this.collection = new ImageCollection();
            this.collection.fetch({
                success: () => {
                    this.render();
                    this.changeSliderImage();
                },
                fail: () => {
                    throw "Error in slider";
                }
            });
        },

        changeSliderImage: function() {
            let slides = document.getElementsByClassName("mySlides");

            if (this.imageCount !== 0) {
                slides[this.imageCount - 1].style.display = "none";
            }

            if (this.imageCount > slides.length - 1) {
                this.imageCount = 0;
            }

            slides[this.imageCount].style.display = "block";

            this.imageCount ++;
            setTimeout(this.changeSliderImage, 4 * 1000);
        },

        render() {
            let viewHtml = '';

            this.collection.models.forEach((image) => {
                viewHtml += ` 
                                <div class="mySlides w3-display-container w3-center">
                                  <img src="${image.attributes.link}" style="width:100%; max-height: 700px">
                                </div>
            `;});

            $(this.el).html(viewHtml);
        }
    });
});