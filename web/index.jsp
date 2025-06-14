<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TaskTrack</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--TailwindCSS CDN-->
        <script src="https://cdn.jsdelivr.net/npm/@tailwindcss/browser@4"></script>
    </head>
    <body class="bg-gray-100 flex items-center justify-center min-h-screen">

        <div class="text-center p-6 bg-white rounded-2xl shadow-lg w-full max-w-md">
            <h1 class="text-4xl font-bold text-indigo-600 mb-4">TaskTrack</h1>
            <p class="text-gray-600 mb-6 text-lg italic">Track your tasks with TaskTrack</p>

            <div class="space-x-4">
                <a href="auth/login.jsp" class="bg-indigo-600 text-white px-4 py-2 rounded hover:bg-indigo-700 transition">Login</a>
                <a href="auth/register.jsp" class="bg-gray-300 text-gray-800 px-4 py-2 rounded hover:bg-gray-400 transition">Register</a>
            </div>
        </div>

    </body>
</html>
