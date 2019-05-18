import { Component, OnInit } from '@angular/core';
import { Chart } from 'chart.js';
import {ActivatedRoute} from "@angular/router";
import {switchMap} from "rxjs/operators";

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.scss']
})
export class ResultsComponent implements OnInit {
  allTheoreticalModelValues: number[];
  allRealModelValues: number[];
  allFrequencies: number[];
  chart = []; // This will hold our chart info

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
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

    this.chart = new Chart('canvas', {
      type: 'line',
      data: {
        labels: allLabels,
        datasets: [
          {
            data: this.allTheoreticalModelValues,
            borderColor: "#3cba9f",
            fill: false
          },
          {
            data: this.allRealModelValues,
            borderColor: "#ffcc00",
            fill: false
          },
        ]
      },
      options: {
        legend: {
          display: false
        },
        scales: {
          xAxes: [{
            display: true
          }],
          yAxes: [{
            display: true
          }],
        }
      }
    });
  }

}
