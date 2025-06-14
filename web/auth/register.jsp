<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
        <!--TailwindCSS CDN-->
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    </head>
    <body>
        <div class="flex flex-col justify-center items-center h-screen">
            <h1 class="text-2xl font-semibold">Register</h1>
            
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <div class="text-red-500 text-sm mb-4"><%= error %></div>
            <% } %>
            
            <form class="mt-6 w-1/3" method="post" action="${pageContext.request.contextPath}/register">
                <div>
                    <label for="name">Name</label>
                    <input type="text" name="name" id="name" class="mt-1 block border border-gray-300 rounded px-2 py-1 text-sm outline-none w-full" />
                </div>
                
                <div class="mt-4">
                    <label for="email">Email</label>
                    <input type="email" name="email" id="email" class="mt-1 block border border-gray-300 rounded px-2 py-1 text-sm outline-none w-full" />
                </div>
                
                <div class="mt-4">
                    <label for="password">Password</label>
                    <input type="password" name="password" id="password" class="mt-1 block border border-gray-300 rounded px-2 py-1 text-sm outline-none w-full" />
                </div>
                
                <button type="submit" class="mt-6 bg-blue-500 text-white py-2 w-full rounded cursor-pointer hover:bg-blue-500/90 transition-colors">Register</button>
            </form>
        </div>
    </body>
</html>
