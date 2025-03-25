@param ai.bhja.calendar.model.OAuthModel  oauthModel
@param String refreshToken
@template.base(content =
@`

    <div>
        <form id="form" action="/refresh" method="get">
            <input type="hidden" name="refreshToken" value="${oauthModel.getRefreshToken()}"/>
            @if(oauthModel!=null)
                <dl>
                    <dt><b><i>Access Token</i></b></dt>
                    <dd>${oauthModel.getAccessToken()}</dd>
                    <dt><b><i>Refresh Token</i></b></dt>
                    <dd>${oauthModel.getRefreshToken()}</dd>
                    <dt><b><i> Token Type</i></b></dt>
                    <dd>${oauthModel.getTokenType()}</dd>
                </dl>
            @else
                <p> No auth details not found values !</p>
            @endif
            <button type="submit"
                    class="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600">
                Refresh
            </button>
        </form>
    </div>
`)

