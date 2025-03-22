<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Expense Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #74ebd5, #acb6e5);
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
            width: 400px;
            border: 5px solid #4CAF50;
        }
        h2 {
            color: #333;
        }
        .report-item {
            background: #f5f5f5;
            margin: 10px 0;
            padding: 10px;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
        }
        .food { background: #ff7043; color: white; }
        .travel { background: #42a5f5; color: white; }
        .utilities { background: #ffca28; }
        .other { background: #9c27b0; color: white; }
        .back-btn {
            display: block;
            margin: 20px auto 0;
            padding: 10px 15px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: bold;
            transition: 0.3s;
        }
        .back-btn:hover {
            background: #45a049;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>Expense Report</h2>
        <div class="report-item">Total Expense: ₹<%= request.getAttribute("total_sum") %></div>
        <div class="report-item food">Food Expense: ₹<%= request.getAttribute("food_sum") %></div>
        <div class="report-item travel">Travel Expense: ₹<%= request.getAttribute("travel_sum") %></div>
        <div class="report-item utilities">Utilities Expense: ₹<%= request.getAttribute("utilities_sum") %></div>
        <div class="report-item other">Other Expenses: ₹<%= request.getAttribute("other_sum") %></div>
        
        <a href="Homepage.html" class="back-btn">Back</a>
    </div>

</body>
</html>
