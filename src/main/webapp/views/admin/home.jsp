<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<div class="main-content">
    <div class="main-content-inner">

```
    <div class="breadcrumbs ace-save-state" id="breadcrumbs">
        <ul class="breadcrumb">
            <li>
                <i class="ace-icon fa fa-home home-icon"></i>
                <a href="#">Trang chủ</a>
            </li>
            <li class="active">Dashboard</li>
        </ul>
    </div>

    <div class="page-content">
        <div class="row">
            <div class="col-xs-12">

                <h2 class="text-primary">📊 Thống kê hệ thống</h2>

                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Revenue By Date</h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <canvas id="revenueChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Order Status Ratio</h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <canvas id="statusChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Top Selling Products</h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <canvas id="topProductChart"></canvas>
                        </div>
                    </div>
                </div>

                <div class="widget-box">
                    <div class="widget-header">
                        <h4 class="widget-title">Revenue By Brand</h4>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <canvas id="brandChart"></canvas>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

</div>
```

</div>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
window.addEventListener("DOMContentLoaded", function() {

    const revenueData = ${empty revenueByDateJson ? '{}' : revenueByDateJson};
    const statusData = ${empty statusRatioJson ? '{}' : statusRatioJson};
    const productData = ${empty topProductsJson ? '{}' : topProductsJson};
    const brandData = ${empty brandRevenueJson ? '{}' : brandRevenueJson};

    const revenueLabels = Object.keys(revenueData);
    const revenueValues = Object.values(revenueData);

    const statusLabels = Object.keys(statusData);
    const statusValues = Object.values(statusData);

    const productLabels = Object.keys(productData);
    const productValues = Object.values(productData);

    const brandLabels = Object.keys(brandData);
    const brandValues = Object.values(brandData);

    if (revenueLabels.length > 0) {
        new Chart(document.getElementById("revenueChart"), {
            type: 'line',
            data: {
                labels: revenueLabels,
                datasets: [{
                    label: 'Revenue',
                    data: revenueValues,
                    tension: 0.3
                }]
            }
        });
    }

    if (statusLabels.length > 0) {
        new Chart(document.getElementById("statusChart"), {
            type: 'pie',
            data: {
                labels: statusLabels,
                datasets: [{ data: statusValues }]
            }
        });
    }

    if (productLabels.length > 0) {
        new Chart(document.getElementById("topProductChart"), {
            type: 'bar',
            data: {
                labels: productLabels,
                datasets: [{
                    label: 'Sold Quantity',
                    data: productValues
                }]
            }
        });
    }

    if (brandLabels.length > 0) {
        new Chart(document.getElementById("brandChart"), {
            type: 'doughnut',
            data: {
                labels: brandLabels,
                datasets: [{ data: brandValues }]
            }
        });
    }

});
</script>
