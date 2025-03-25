@import java.lang.String

@param String url

@template.base(content = @`
    <div class="bg-white shadow rounded p-4">
        <form action="/token">
            <a href="${url}">Login with Active Directory</a>
        </form>
    </div>
`)
