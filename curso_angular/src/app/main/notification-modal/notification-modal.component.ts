import { NgClass, NgFor, NgIf, NgSwitch, NgSwitchCase, NgSwitchDefault } from '@angular/common';
import { Component } from '@angular/core';
import { NotificationService } from 'src/app/common-services/notification.service';

@Component({
  selector: 'app-notification-modal',
  standalone: true,
  imports: [NgIf, NgClass, NgFor, NgSwitch, NgSwitchCase, NgSwitchDefault],
  templateUrl: './notification-modal.component.html',
  styleUrl: './notification-modal.component.css'
})
export class NotificationModalComponent {
  constructor(private vm: NotificationService) { }
  
  public get VM() { 
    return this.vm; 
  }
}