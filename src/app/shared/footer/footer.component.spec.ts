import {
  ComponentFixture,
  TestBed
} from '@angular/core/testing';
import { FooterComponent } from './footer.component';
import { randomIntFromInterval } from 'src/app/test/aRandom';

describe('FooterComponent', () => {
  let component: FooterComponent;
  let fixture: ComponentFixture<FooterComponent>;
  let elements: any;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FooterComponent]
    });
    fixture = TestBed.createComponent(FooterComponent);
    component = fixture.componentInstance;
    elements = {
      yearLabel: () => fixture.nativeElement.querySelector('.float-none').textContent
    }
  });

  it('should create the footer component', () => {
    fixture.detectChanges();
    expect(component).toBeTruthy();
  });

  it('should show current year', () => {
    component.year = randomIntFromInterval(2000, 3000);
    fixture.detectChanges();
    expect(elements.yearLabel()).toContain(component.year);
  })
});
