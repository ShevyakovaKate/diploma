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
  parameters: Parameter[];
  hi2: number;

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.initGraphic();
    this.initParameterTable();
  }

  initGraphic() {
    if(localStorage.getItem('allFrequenciesffs')) {
      this.allFrequencies = JSON.parse(localStorage.getItem('allFrequenciesffs'));
    }
    if(localStorage.getItem('allTheoreticalModelValuesffs')) {
      this.allTheoreticalModelValues = JSON.parse(localStorage.getItem('allTheoreticalModelValuesffs'));
    }
    if(localStorage.getItem('allRealModelValuesffs')) {
      this.allRealModelValues = JSON.parse(localStorage.getItem('allRealModelValuesffs'));
    }

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
          text: 'Dependence of phase shift on frequency'
        },
        legend: {
          display: true
        },
        scales: {
          xAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'Frequency ()'
            }
          }],
          yAxes: [{
            scaleLabel: {
              display: true,
              labelString: 'Phase shift ()'
            }
          }],
        }
      }
    });
  }

  initParameterTable() {
    if(localStorage.getItem('parameters')) {
      this.parameters = JSON.parse(localStorage.getItem('parametersffs'));
      console.log(this.parameters);
    }
    if(localStorage.getItem('hi2ffs')) {
      this.hi2 = JSON.parse(localStorage.getItem('hi2'));
    }
  }
}
