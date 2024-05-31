import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CasillaService } from '../../../../shared/services/casilla-service';
import { Casilla } from '../../../../shared/api-objects/casilla';

@Component({
  selector: 'app-casilla',
  templateUrl: './casilla.component.html',
  styleUrls: ['./casilla.component.scss']
})
export class CasillaComponent implements OnInit {

  public casillas: Casilla[] = [];

  constructor(
    private casillaService: CasillaService,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    this.casillaService.counter()
    .subscribe(response => {
      this.casillas = response;
      console.log(this.casillas);
    });
  }
}
