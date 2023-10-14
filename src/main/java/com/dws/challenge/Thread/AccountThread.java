package com.dws.challenge.Thread;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicReference;

import com.dws.challenge.domain.Account;
import com.dws.challenge.service.NotificationService;

public class AccountThread implements Runnable {

	private Account fromAccount;
	private Account toAccount;
	private AtomicReference<BigDecimal> amount;
	private NotificationService notificationService;

	public AccountThread(Account fromAccount, Account toAccount, AtomicReference<BigDecimal> balance,
			NotificationService notificationService) {

		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = balance;
		this.notificationService = notificationService;

	}

	@Override
	public void run() {
		
		if (fromAccount.getAccountId().compareTo(toAccount.getAccountId()) < 0) {
			synchronized (fromAccount) {
				synchronized (toAccount) {
					fromAccount.withdraw(amount.get());
					notificationService.notifyAboutTransfer(fromAccount,
							"The amount " + amount.get() + " is deducted from Account id " + fromAccount.getAccountId()
									+ " on " + LocalDateTime.now());
					toAccount.deposit(amount.get());

					notificationService.notifyAboutTransfer(toAccount, "The amount " + amount.get()
							+ " is creadited to Account id " + toAccount.getAccountId() + " on " + LocalDateTime.now());

				}
			}
		} else {
			synchronized (toAccount) {
				synchronized (fromAccount) {
					fromAccount.withdraw(amount.get());
					notificationService.notifyAboutTransfer(fromAccount,
							"The amount " + amount.get() + " is deducted from Account id " + fromAccount.getAccountId()
									+ " on " + LocalDateTime.now());
					toAccount.deposit(amount.get());
					notificationService.notifyAboutTransfer(toAccount, "The amount " + amount.get()
							+ " is creadited to Account id " + toAccount.getAccountId() + " on " + LocalDateTime.now());

				}
			}
		}
	}

}
