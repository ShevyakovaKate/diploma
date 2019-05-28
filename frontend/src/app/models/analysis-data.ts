import {Parameter} from "./parameter";

export class AnalysisData {
  private _parameters: Parameter[] ;
  private _hi2: number;
  private _autocorrelationalFunction: number[];
  private _weightedAverageBalances: number[]


  get parameters(): Parameter[] {
    return this._parameters;
  }

  set parameters(value: Parameter[]) {
    this._parameters = value;
  }

  get hi2(): number {
    return this._hi2;
  }

  set hi2(value: number) {
    this._hi2 = value;
  }


  get autocorrelationalFunction(): number[] {
    return this._autocorrelationalFunction;
  }

  set autocorrelationalFunction(value: number[]) {
    this._autocorrelationalFunction = value;
  }

  get weightedAverageBalances(): number[] {
    return this._weightedAverageBalances;
  }

  set weightedAverageBalances(value: number[]) {
    this._weightedAverageBalances = value;
  }
}
