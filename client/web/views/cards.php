<!-- HTML template for error cards -->
<template id="error-card-template">
	<div class="wide-card error-card mdl-card mdl-shadow--2dp">
		<div class="mdl-card__title">
			<h2 class="mdl-card__title-text">Ooops</h2>
		</div>
		<div class="mdl-card__supporting-text">
			Une erreur s'est produite : <b class="error-message">ERROR</b><br />
			<p class="error-more-information">MORE_INFORMATION</p>
			<br />
			<br />
		</div>
		<div class="mdl-card__actions mdl-card--border">
			<a class="mdl-button mdl-js-button mdl-js-ripple-effect error-action">
				ACTION
			</a>
		</div>
		<div class="mdl-card__menu">
			<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect">
				<i class="material-icons">loop</i>
			</button>
		</div>
	</div>		
</template>

<!-- HTML template for info cards -->
<template id="info-card-template">
	<div class="wide-card dark-card mdl-card mdl-shadow--2dp">
		<div class="mdl-card__title">
			<h2 class="mdl-card__title-text info-title">TITLE</h2>
		</div>
		<div class="mdl-card__supporting-text info-text">
			<!-- INFO -->
			<br />
			<br />
		</div>
		<div class="mdl-card__actions mdl-card--border">
			<button class="mdl-button mdl-js-button mdl-js-ripple-effect info-action">
				ACTION
			</button>
		</div>
		<div class="mdl-card__menu">
			<button class="mdl-button mdl-button--icon mdl-js-button mdl-js-ripple-effect info-icon">
				<i class="material-icons">loop</i>
			</button>
		</div>
	</div>		
</template>