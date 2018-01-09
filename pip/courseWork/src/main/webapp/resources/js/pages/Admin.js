$(document).ready(() => {
    window.Admin = Backbone.View.extend({
        el: $('body'),

        events: {
            'click .close': 'w3_close',
            'click .open': 'w3_open',
        },

        initialize() {
            _.bindAll(this, 'render');

            this.render();
        },

        w3_open() {
            document.getElementById("mySidebar").style.display = "block";
            document.getElementById("myOverlay").style.display = "block";
        },

        w3_close() {
            document.getElementById("mySidebar").style.display = "none";
            document.getElementById("myOverlay").style.display = "none";
        },

        render() {
            $(this.el).append(`
                    <!-- Sidebar/menu -->
            <nav class="w3-sidebar w3-purple w3-collapse w3-opacity w3-top w3-large w3-padding"
                 style="z-index: 3; width: 300px; font-weight: bold; display: none;" id="mySidebar"><br>
                <a href="javascript:void(0)" class="close w3-button w3-black w3-hide-large w3-display-topleft"
                   style="width:100%;font-size:22px">Close Menu</a>
            
            
                <div class="w3-bar-block w3-margin">
                    <a href="index.html" class="close w3-border-bottom w3-border-black w3-bar-item w3-button w3-hover-white">Главная</a>
                    <a href="#news" class="close w3-border-bottom w3-border-black w3-bar-item w3-button w3-hover-white">Новости</a>
                    <a href="" class="close w3-bar-item w3-button w3-hover-white">Легенды</a>
                </div>
            </nav>
            
            <!-- Top menu on small screens -->
            <header class="w3-container w3-top w3-hide-large w3-black w3-xlarge w3-padding">
                <a href="javascript:void(0)" class="open w3-button w3-purple w3-margin-right">☰</a>
            </header>
            
            <!-- Overlay effect when opening sidebar on small screens -->
            <div class="close w3-overlay w3-hide-large" style="cursor: pointer; display: none;"
                 title="close side menu" id="myOverlay"></div>
            
            <!-- !PAGE CONTENT! -->
            <div class="w3-main" style="margin-left:340px;margin-right:40px">
                <!-- News -->
                <div class="w3-container" id="news" style="margin-top:75px">
                    <h1 class="w3-xxxlarge"><b>Новости</b></h1>
                    <hr style="width:50px;border:5px solid" class="w3-round">
                </div>
            
                <div class="w3-container w3-purple">
                    <h2>Input Form</h2>
                </div>
            
                <div class="w3-container w3-card-4">
                    <br/>
            
                    <p>
                        <label class="w3-text-grey">Image link</label>
                        <input class="w3-input w3-border _link" type="text" required="">
                    </p>
            
                    <p>
                        <label class="w3-text-grey">Description</label>
                        <textarea class="w3-input w3-border _description" style="height: 200px;resize:none;"></textarea>
                    </p> <br/>
            
                    <p>
                        <button type="button" class="w3-btn w3-padding w3-purple send" style="width:120px">Send &nbsp; ❯</button>
                    </p>
                </div>
            </div>
        `);
        },
    });
});