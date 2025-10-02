import React, { useEffect, useRef } from 'react';
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  DoughnutController
} from 'chart.js';
ChartJS.register(ArcElement, Tooltip, Legend, DoughnutController);

const ChartComponent = ({ assets = [] }) => {
  const chartRef = useRef(null);
  const chartInstance = useRef(null);

  useEffect(() => {
    if (!assets || assets.length === 0) {
      return;
    }

    const ctx = chartRef.current.getContext('2d');
    if (chartInstance.current) {
      chartInstance.current.destroy();
    }
    const labels = assets.map(asset => asset.ticker);
    const data = assets.map(asset => asset.totalValue || asset.currentPrice * asset.quantity);
    const backgroundColors = [
      '#FF6384', '#36A2EB', '#FFCE56', '#4BC0C0', 
      '#9966FF', '#FF9F40', '#FF6384', '#C9CBCF'
    ];

    chartInstance.current = new ChartJS(ctx, {
      type: 'doughnut',
      data: {
        labels: labels,
        datasets: [
          {
            data: data,
            backgroundColor: backgroundColors,
            borderWidth: 2,
            borderColor: '#fff'
          }
        ]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom'
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                const label = context.label || '';
                const value = context.parsed;
                const total = context.dataset.data.reduce((a, b) => a + b, 0);
                const percentage = ((value / total) * 100).toFixed(1);
                return `${label}: $${value.toFixed(2)} (${percentage}%)`;
              }
            }
          }
        }
      }
    });
    return () => {
      if (chartInstance.current) {
        chartInstance.current.destroy();
      }
    };
  }, [assets]);

  if (!assets || assets.length === 0) {
    return (
      <div className="p-4 text-center text-gray-500">
        No data available for chart
      </div>
    );
  }

  return (
    <div className="mt-8">
      <h2 className="text-xl font-bold mb-4">Portfolio Allocation</h2>
      <div className="bg-white p-4 rounded-lg shadow-md">
        <div style={{ height: '400px' }}>
          <canvas ref={chartRef} />
        </div>
      </div>
    </div>
  );
};

export default ChartComponent;