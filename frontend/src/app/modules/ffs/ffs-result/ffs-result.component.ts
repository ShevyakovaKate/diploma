import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import {Parameter} from "../../../models/parameter";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-ffs-result',
  templateUrl: './ffs-result.component.html',
  styleUrls: ['./ffs-result.component.scss']
})
export class FfsResultComponent implements OnInit {

  allTheoreticalModelValues: number[];
  allRealModelValues: number[];
  allFrequencies: number[];
  chart = []; // This will hold our chart info
  autocorrelationalFunction = [];
  weightedAverageBalances = [];
  parameters: Parameter[];
  hi2: number;
  showGraphic = false;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.initGraphic();
    this.initParameterTable();
  }

  initGraphic() {
    if(localStorage.getItem('allFrequenciesffs')) {

      if(localStorage.getItem('allFrequenciesffs')) {
        this.allFrequencies = JSON.parse(localStorage.getItem('allFrequenciesffs'));
      }
      if(localStorage.getItem('allTheoreticalModelValuesffs')) {
        this.allTheoreticalModelValues = JSON.parse(localStorage.getItem('allTheoreticalModelValuesffs'));
      }
      if(localStorage.getItem('allRealModelValuesffs')) {
        this.allRealModelValues = JSON.parse(localStorage.getItem('allRealModelValuesffs'));
      }

      this.showGraphic = true;
      let allLabels = [];
      this.allFrequencies.forEach((res) => {
        allLabels.push(res.toString())
      });

      this.chart = new Chart('canvas', {
        type: 'line',
        data: {
          labels: allLabels,
          datasets: [
            {
              data: this.allTheoreticalModelValues,
              label: "Theoretical model",
              borderColor: "#3cba9f",
              fill: false
            },
            {
              data: this.allRealModelValues,
              label: "Real model",
              borderColor: "#ffcc00",
              fill: false
            },
          ]
        },
        options: {
          title: {
            display: true,
            text: 'Autocorrelation function'
          },
          legend: {
            display: true
          },
          scales: {
            xAxes: [{
              scaleLabel: {
                display: true,
                labelString: 'Time (ms)'
              }
            }],
            yAxes: [{
              scaleLabel: {
                display: true
              }
            }],
          }
        }
      });

      this.autocorrelationalFunction = new Chart('autocorrelationalFunction', {
        type: 'line',
        data: {
          labels: allLabels,
          datasets: [
            {
              data: JSON.parse(localStorage.getItem('autocorrelationalFunctionffs')),
              borderColor: "#3cba9f",
              fill: false,
              lineTension: 0
            }
          ]
        },
        options: {
          title: {
            display: true,
            text: 'Auto Correlation of Residuals'
          },
          legend: {
            display: true
          },
          scales: {
            xAxes: [{
              scaleLabel: {
                display: true
              }
            }],
            yAxes: [{
              scaleLabel: {
                display: true
              }
            }],
          }
        }
      });



      this.weightedAverageBalances = new Chart('weightedAverageBalances', {
        type: 'line',
        data: {
          labels: allLabels,
          datasets: [
            {
              data: JSON.parse(localStorage.getItem('weightedAverageBalancesffs')),
              borderColor: "#3cba9f",
              fill: false,
              lineTension: 0
            }
          ]
        },
        options: {
          title: {
            display: true,
            text: 'Residuals'
          }
        }
      });
    }



    console.log(this.chart);
  }

  initParameterTable() {
    if(localStorage.getItem('parametersffs')) {
      this.parameters = JSON.parse(localStorage.getItem('parametersffs'));
      console.log(this.parameters);
    }
    if(localStorage.getItem('hi2ffs')) {
      this.hi2 = JSON.parse(localStorage.getItem('hi2ffs'));
    }
  }
}
