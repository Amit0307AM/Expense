<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<html>
<head>
    <title>Expense Report</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script> <!-- Chart.js -->
    <style>
        table { width: 50%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid black; padding: 8px; text-align: center; }
        th { background-color: #f2f2f2; }
    </style>
</head>
<body>
    <h2>Expense Report</h2>

    <!-- Table -->
    <table>
        <tr>
            <th>Category</th>
            <th>Total Amount</th>
        </tr>
        <%
            ResultSet rs = (ResultSet) request.getAttribute("resultSet");
            String categories = "";
            String values = "";
            try {
                while (rs != null && rs.next()) {
                    categories += "'" + rs.getString("category") + "',";
                    values += rs.getDouble("total_amount") + ",";
        %>
                    <tr>
                        <td><%= rs.getString("category") %></td>
                        <td><%= rs.getDouble("total_amount") %></td>
                    </tr>
        <%
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        %>
    </table>

    <!-- Chart -->
    <canvas id="expenseChart" width="400" height="200"></canvas>
    <script>
        var ctx = document.getElementById('expenseChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'pie', // Change to 'bar' if needed
            data: {
                labels: [<%= categories %>],
                datasets: [{
                    label: 'Total Expenses',
                    data: [<%= values %>],
                    backgroundColor: ['red', 'blue', 'green', 'orange', 'purple']
                }]
            }
        });
    </script>
</body>
</html>
