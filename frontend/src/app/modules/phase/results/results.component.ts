import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";
import {Parameter} from "../../../models/parameter";

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {
  allTheoreticalModelValues: number[];
  allRealModelValues: number[];
  allFrequencies: number[];
  model = []; // This will hold our chart info
  autocorrelationalFunction = []; // This will hold our chart info
  weightedAverageBalances = []; // This will hold our chart info
  parameters: Parameter[];
  hi2: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.initGraphic();
    this.initParameterTable();
  }

  initGraphic() {
    if(localStorage.getItem('allFrequencies')) {
      this.allFrequencies = JSON.parse(localStorage.getItem('allFrequencies'));
    }
    if(localStorage.getItem('allTheoreticalModelValues')) {
      this.allTheoreticalModelValues = JSON.parse(localStorage.getItem('allTheoreticalModelValues'));
    }
    if(localStorage.getItem('allRealModelValues')) {
      this.allRealModelValues = JSON.parse(localStorage.getItem('allRealModelValues'));
    }

    let allLabels = [];
    this.allFrequencies.forEach((res) => {
      allLabels.push(res.toString())
    });

    this.model = new Chart('model', {
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
          text: 'Dependence of phase shift on frequency'
        },
        legend: {
          display: true
        },
        scales: {
          xAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'Frequency (rad/s)'
            }
          }],
          yAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'Phase shift (ns)'
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
            data: JSON.parse(localStorage.getItem('autocorrelationalFunction')),
            borderColor: "#3cba9f",
            fill: false
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
            data: JSON.parse(localStorage.getItem('weightedAverageBalances')),
            borderColor: "#3cba9f",
            fill: false
          }
        ]
      },
      options: {
        title: {
          display: true,
          text: 'Residuals'
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

    console.log(this.weightedAverageBalances);
  }

  initParameterTable() {
    if(localStorage.getItem('parameters')) {
      this.parameters = JSON.parse(localStorage.getItem('parameters'));
      console.log(this.parameters);
    }
    if(localStorage.getItem('hi2')) {
      this.hi2 = JSON.parse(localStorage.getItem('hi2'));
    }
  }
}
